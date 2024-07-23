package com.example.funtime_app.config;

import com.example.funtime_app.entity.*;
import com.example.funtime_app.entity.enums.RoleName;
import com.example.funtime_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryTagRepository categoryTagRepository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final VideoRepository videoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {


//        Role role1 = Role.builder()
//                .roleName(RoleName.ROLE_ADMIN)
//                .build();
//        Role role2 = Role.builder()
//                .roleName(RoleName.ROLE_USER)
//                .build();
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//
//        User user1 = User.builder()
//                .username("user")
//                .roles(List.of(role2))
//                .password(passwordEncoder.encode("123"))
//                .build();
//          User user2 = User.builder()
//                .username("admin")
//                  .roles(List.of(role1))
//                .password(passwordEncoder.encode("123"))
//                .build();
//
//          userRepository.save(user1);
//          userRepository.save(user2);




        // byte[] bytes1 = Files.readAllBytes(Path.of("com/example/funtime_app/config/rasm.jpg"));

//       Path path = Paths.get("src/main/java/com/example/funtime_app/config/", "video.mp4");
//                byte[] bytes = Files.readAllBytes(path);
//
//        Attachment attachment = Attachment.builder()
//                .content(bytes)
//                .contentType("mp4")
//                .build();
//        attachmentRepository.save(attachment);
//        Video video = Video.builder()
//               .attachment(attachment)
//               .build();
//        videoRepository.save(video);

//        byte[] bytes = Files.readAllBytes(path);
//        Random random = new Random();
//
//
//        for (int i = 0; i < 2000; i++) {
//
//            Attachment attachment = Attachment.builder()
//                    .contentType("jpeg")
//                    .content(bytes)
//                    .build();
//
//            attachmentRepository.save(attachment);
//
//            User user = User.builder()
//                    .email("salom"+i+"@gmail.com")
//                    .firstName("FirstName"+i)
//                    .lastName("LastName"+i)
//                    .profilePhoto(attachment)
//                    .password("Root123")
//                    .username("username"+i)
//                    .build();
//            userRepository.save(user);
//
//        }
//
//        for (int i = 0; i < 50; i++) {
//
//            CategoryTag categoryTag = CategoryTag.builder()
//                    .tagName("Tag"+i)
//                    .build();
//
//            categoryTagRepository.save(categoryTag);
//
//        }
//
//        List<CategoryTag> tags = categoryTagRepository.findAll();
//        List<User> users = userRepository.findAll();
//
//
//        for (int i = 0; i < 10; i++) {
//
//            Attachment attachment = Attachment.builder()
//                    .content(bytes)
//                    .contentType("jpeg")
//                    .build();
//
//            attachmentRepository.save(attachment);
//
//            Category category = Category.builder()
//                    .name("Category"+i)
//                    .tags(List.of(
//                            tags.get(random.nextInt(1,49))
//                            ))
//                    .attachment(attachment)
//                    .build();
//
//            categoryRepository.save(category);
//
//        }
//
//        List<Category> categories = categoryRepository.findAll();
//        LocalDateTime now = LocalDateTime.now();
//
//        for (int i = 0; i < 10000; i++) {
//
//            Attachment attachment = Attachment.builder()
//                    .content(bytes)
//                    .contentType("jpeg")
//                    .build();
//
//            attachmentRepository.save(attachment);
//
//            Post post = Post.builder()
//                    .attachment(attachment)
//                    .description("Description "+i)
//                    .title("Title "+i)
//                    .user(users.get(random.nextInt(1, 2000)))
//                    .category(categories.get(random.nextInt(1,10)))
//                    .views(random.nextInt(2000, 231234))
//                    .createdAt(now.minusDays(random.nextInt(1, 50)))
//                    .build();
//
//            postRepository.save(post);
//
//        }

//        Path path = Paths.get("src/main/java/com/example/funtime_app/config/", "video.mp4");
//        byte[] bytes = Files.readAllBytes(path);
//        List<User> users = userRepository.findAll();
//
//        for (int i = 0; i < 1000; i++) {
//
//        Attachment attachment = Attachment.builder()
//                .content(bytes)
//                .contentType("mp4")
//                .build();
//        attachmentRepository.save(attachment);
//        Video video = Video.builder()
//               .attachment(attachment)
//                .user(users.get(new Random().nextInt(1, 30)))
//               .build();
//        videoRepository.save(video);
//
//        }




    }
}
