package study.donshin.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.donshin.domain.post.Post;
import study.donshin.domain.post.PostService;
import study.donshin.s3client.S3Uploader;

import java.io.IOException;
import java.nio.ByteBuffer;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;

    @ResponseBody
    @PostMapping(value = "/post", consumes = "multipart/form-data")
    public String save(@ModelAttribute CreatePostForm form) throws IOException {

        ByteBuffer fileBody = ByteBuffer.wrap(form.getFile().getBytes());
        String fileContentType = form.getFile().getContentType();
        long fileContentLength = form.getFile().getSize();
        String uploadedFileName = s3Uploader.upload(fileBody, fileContentType, fileContentLength);

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setUploadedFileName(uploadedFileName);

        postService.save(post);
        return uploadedFileName;
    }
}
