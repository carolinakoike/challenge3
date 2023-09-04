/*package com.compass.challenge3.client;

import com.compass.challenge3.model.Post;
import com.compass.challenge3.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Component
@FeignClient (value = "posts", url = "https://jsonplaceholder.typicode.com/posts")

public interface ImportPosts {

    @GetMapping(value = "/{id}")
    Post getPostById(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/comments")
    List<Comment> getCommentById(@PathVariable("id") Long id);

    Post getPostbyId(Long id);

    List<Comment> getCommentbyId(Long postId);
}*/

package com.compass.challenge3.client;

        import com.compass.challenge3.model.Comment;
        import com.compass.challenge3.model.Post;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.stereotype.Component;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import java.util.List;

@Component // Adicione esta anotação para que o Spring gerencie ImportPosts como um bean.
@FeignClient(value = "posts", url = "https://jsonplaceholder.typicode.com/posts")
public interface ImportPosts {
    @GetMapping(value = "/{id}")
    Post getPostById(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/comments")
    List<Comment> getCommentById(@PathVariable("id") Long id);

    Post getPostbyId(Long id);

    List<Comment> getCommentbyId(Long postId);
}
