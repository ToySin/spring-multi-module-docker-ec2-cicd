package study.donshin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.ByteBuffer;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Upload s3Upload;

    @ResponseBody
    @PostMapping(value = "/post", consumes = "multipart/form-data")
    public String save(@RequestBody CreatePostForm form) throws IOException {
        ByteBuffer fileBody = ByteBuffer.wrap(form.getFile().getBytes());
        String fileContentType = form.getFile().getContentType();
        long fileContentLength = form.getFile().getSize();
        String uploadedFileName = s3Upload.upload(fileBody, fileContentType, fileContentLength);

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setUploadedFileName(uploadedFileName);

        postService.save(post);
        return uploadedFileName;
    }
}