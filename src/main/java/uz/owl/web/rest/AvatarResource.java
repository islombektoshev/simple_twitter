package uz.owl.web.rest;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.owl.domain.Avatar;
import uz.owl.service.AvatarService;
import uz.owl.service.dto.AvatarDTO;

@RestController
@RequestMapping("/api/v1")
public class AvatarResource {

    @Autowired
    private AvatarService avatarService;


    @PostMapping("/uploadFile")
    public ResponseEntity<AvatarDTO> uploadImage(@RequestParam("file") MultipartFile file) {

        Avatar avatar = avatarService.storeFile(file);

        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setImageId(avatar.getAvatarId());
        return ResponseEntity.ok(avatarDTO);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // Load file from database
        Avatar dbFile = avatarService.getFile(fileId);

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
            .body(new ByteArrayResource(dbFile.getData()));
    }
}
