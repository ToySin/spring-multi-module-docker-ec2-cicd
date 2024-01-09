package study.donshin;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class CreatePostForm {

    private String title;
    private MultipartFile file;
}
