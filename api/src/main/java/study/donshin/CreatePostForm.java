package study.donshin;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class CreatePostForm {

    private String title;
    private MultipartFile file;
}
