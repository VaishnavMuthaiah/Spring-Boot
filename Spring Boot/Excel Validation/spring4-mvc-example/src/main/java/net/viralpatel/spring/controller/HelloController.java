package net.viralpatel.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import net.viralpatel.spring.excel.ExcelPOIHelper;
import net.viralpatel.spring.excel.MyCell;

@Controller
public class HelloController {

    boolean stop = false;
    String uploadedFolderName = "";
    String fileNameOne = "";
    String fileNameTwo = "";
    String esaTimesheetHeaders = "";
    String fieldglassHeaders = "";
    List<String> esaHeadersList = new ArrayList<String>();
    List<String> fieldglassHeadersList = new ArrayList<String>();
    int i = 0;

    @Resource(name = "excelPOIHelper")

    ExcelPOIHelper excelPOIHelper;
    private static String UPLOADED_FOLDER2 = "E://Temp//UploadFiles//Book3.xlsx";
    private static String fileLocation = "E://Temp//UploadFiles//";

    @GetMapping("/")
    public String index() {
	return "welcome";
    }

    @Autowired
    public Environment env;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String multiFileUpload(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes,
	    ModelMap model) throws IOException {
	StringJoiner sj = new StringJoiner(" , ");
	for (MultipartFile file : files) {
	    i++;
	    if (file.isEmpty()) {
		continue; // next please
	    }

	    try {

		byte[] bytes = file.getBytes();
		Path path = Paths.get(fileLocation + file.getOriginalFilename());
		Files.write(path, bytes);
		if (i == 1) {
		    uploadedFolderName = file.getOriginalFilename();
		    fileNameOne = fileLocation + uploadedFolderName;
		    sj.add(file.getOriginalFilename());
		}
		if (i == 2) {
		    uploadedFolderName = file.getOriginalFilename();
		    fileNameTwo = fileLocation + uploadedFolderName;
		    sj.add(file.getOriginalFilename());
		}

	    } catch (IOException e) {
		e.printStackTrace();
	    }

	}

	String uploadedFileName = sj.toString();
	if (!StringUtils.isEmpty(uploadedFileName)) {
	    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + uploadedFileName);
	} else {
	    redirectAttributes.addFlashAttribute("message", "You haven't select required files");
	}
	return "redirect:/indexStatus";
    }

    @GetMapping("/indexStatus")
    public String uploadStatus(Model model, RedirectAttributes redirectAttributes) throws IOException {
	if ((fileNameOne.endsWith(".xlsx") || fileNameOne.endsWith(".xls"))
		&& (fileNameTwo.endsWith(".xlsx") || fileNameTwo.endsWith(".xls"))) {
	    esaTimesheetHeaders = env.getProperty("esaTimesheet");
	    esaHeadersList = Arrays.asList(esaTimesheetHeaders.split(","));
	    fieldglassHeaders = env.getProperty("fieldglassTimesheet");
	    fieldglassHeadersList = Arrays.asList(fieldglassHeaders.split(","));
	    model.addAttribute("esaHeadersList", esaHeadersList);
	    model.addAttribute("fieldglassHeadersList", fieldglassHeadersList);
	}
	return "welcome";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getSelectedData")
    public String getSelectedData(@RequestParam("selectedHeaderExcelOne") String selectedHeaderExcelOne,
	    @RequestParam("projectId") String projectId,
	    @RequestParam("selectedHeaderExcelTwo") String selectedHeaderExcelTwo, @RequestParam("reportType") String reportType, Model model,
	    RedirectAttributes redirectAttributes) throws IOException {
	if (fileNameOne != null && fileNameTwo != null) {
	    if ((fileNameOne.endsWith(".xlsx") || fileNameOne.endsWith(".xls"))
		    && (fileNameTwo.endsWith(".xlsx") || fileNameTwo.endsWith(".xls"))) {
		Map<Integer, List<MyCell>> data = excelPOIHelper.readExcel(fileNameOne, fileNameTwo, UPLOADED_FOLDER2,
			selectedHeaderExcelOne, selectedHeaderExcelTwo, projectId, reportType );
		model.addAttribute("data", data);
		model.addAttribute("esaHeadersList", esaHeadersList);
		model.addAttribute("fieldglassHeadersList", fieldglassHeadersList);
	    } else {
		model.addAttribute("message", "Not a valid excel file!");
	    }
	} else {
	    model.addAttribute("message", "File missing! Please upload an excel file.");
	}
	return "welcome";
    }

};
