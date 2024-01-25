package com.senfiles.version1.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.senfiles.version1.Dto.FileDto;
import com.senfiles.version1.Dto.Userdto;
import com.senfiles.version1.Model.File;
import com.senfiles.version1.Model.Logs;
import com.senfiles.version1.Model.UserModel;
import com.senfiles.version1.service.FileService;
import com.senfiles.version1.service.LogsService;
import com.senfiles.version1.service.UserService;
import jakarta.validation.Valid;



//@CrossOrigin(origins = "http://localhost:80")
//#@RequestMapping("/myapp")
@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private LogsService logsService;

  
    @GetMapping("/login")
    public String loginForm(@RequestParam(name = "error", required = false) String error) {
       return "login";
    }

   
    @GetMapping("/admin")
    public String listFiles(Model model) {
        try {
            List<FileDto> files = fileService.getFiles();
            model.addAttribute("files", files);
            return "admin";
        } catch (DataAccessException e) {
            return "redirect:/admin?error=error_database";
        }
    }

    // @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String listFilesuser(Model model) {
        try {
            List<FileDto> files = fileService.getFiles();
            model.addAttribute("files", files);
            return "user";
        } catch (DataAccessException e) {
            return "redirect:/user?error=error_database";
        }
    }

    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadContent(@PathVariable(value = "id") Long id) {

        try {
            Optional<File> file = fileService.find_id(id);

            if (file.isPresent()) {
                byte[] bytes = file.get().getCont();
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + file.get().getName()+".zip" + "\"")
                        .body(bytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();

        }

    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public String uploadFiles(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("application/zip")) {
            return "redirect:/admin?error=invalid_file_type";
        }

        if (file.getSize() > 16777216) {
            return "redirect:/admin?error=error_maxim_excess";
        }

        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(0, 20) + "...";
        File Newfile = new File();
        Newfile.setName(fileName);

        try {

            // Newfile.setCont(file.getBytes());
            Newfile.setCont(file.getBytes());
            fileService.Filesave(Newfile);
            return "redirect:/admin?success=true";
        } catch (IOException e) {
            return "redirect:/admin?error=error_to_save";
        } catch (DataAccessException e) {
            return "redirect:/admin?error=error_to_save";
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteContent(@PathVariable(value = "id") Long id) {

        try {
            Optional<File> file = fileService.find_id(id);

            if (file.isPresent()) {
                fileService.delete(file.get());
                return "redirect:/admin?sucess=sucess_delete";
            } else {
                return "redirect:/admin?error=file_empty";
            }
        } catch (DataAccessException e) {
            return "redirect:/admin?error=error_delete";
        }

    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        Userdto user = new Userdto();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") Userdto userDto,
            BindingResult result,
            Model model) {

        try {
            Optional<UserModel> existingUser = userService.findUser(userDto.getUsername());

            if (existingUser.isPresent()) {
                return "redirect:/registration?fail";
            } else {

                if (result.hasErrors()) {
                    return "redirect:/registration?fail";
                }

                userService.saveUser(userDto);
                return "redirect:/registration?success";
            }
        } catch (DataAccessException e) {
            return "redirect:/registration?fail";
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/logs")
    public ResponseEntity<ByteArrayResource> downloadLogs() {
        try {

            List<Logs> logs = logsService.getFiles();

            String logsTable = logsService.getLogsTable(logs);

            ByteArrayResource resource = new ByteArrayResource(logsTable.getBytes());

            System.out.println(resource.contentLength());
            return ResponseEntity.ok()

                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=logs.txt")
                    .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

}


