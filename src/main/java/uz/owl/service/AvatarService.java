package uz.owl.service;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.owl.domain.Avatar;
import uz.owl.repository.AvatarRepository;

import java.io.IOException;
import java.security.PublicKey;

@Service
public class AvatarService {


    @Autowired
    private AvatarRepository avatarRepository;

    public Avatar storeFile(MultipartFile file) {
        Avatar save = null;
        try {
            Avatar avatar = new Avatar(file.getBytes());

            save = avatarRepository.save(avatar);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return save;
    }

    public Avatar getFile(Long id) {
        return avatarRepository.findById(id).get();
    }

}
