package study.donshin.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

        private final PostRepository postRepository;

        public Post save(Post post) {
            return postRepository.save(post);
        }

        public Optional<Post> findByTitle(String title) {
            return postRepository.findByTitle(title);
        }
}
