package com.example.funtime_app.services;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.dto.UserEditDTO;
import com.example.funtime_app.dto.request.ChangePhotoDTO;
import com.example.funtime_app.dto.request.ResendCodeDTO;
import com.example.funtime_app.dto.response.UserResponseDTO;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.entity.enums.OTPStatus;
import com.example.funtime_app.entity.enums.UserStatus;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.projection.UserEditProjection;
import com.example.funtime_app.projection.UserProfileProjection;
import com.example.funtime_app.repository.*;
import com.github.fashionbrot.annotation.Valid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OTPService otpService;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;
    private final CodeRepository codeRepository;
    private final BannerRepository bannerRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public HttpEntity<?> saveUser(@Valid UserDTO userDTO) {
        try {

            if (!isValidPassword(userDTO.password())) {
                return ResponseEntity.badRequest().body("Password is invalid");
            }

            User byUsername = userRepository.findByUsername(userDTO.username());
            if (byUsername!=null){
                return ResponseEntity.badRequest().body("username invalid");
            }
            List<User> byEmail = userRepository.findByEmail(userDTO.email());
            if (!byEmail.isEmpty()) {
                return ResponseEntity.status(401).body("This email already verification");
            }

            Optional<Role> byId = roleRepository.findById(2);
            System.out.println(byId.get());
            User user = User.builder()
                    .profilePhoto(null)
                    .username(userDTO.username())
                    .firstName(userDTO.firstName())
                    .email(userDTO.email())
                    .lastName(userDTO.lastName())
                    .status(UserStatus.NOACTIVE)
                    .roles(List.of(byId.get()))
                    .password(passwordEncoder.encode(userDTO.password()))
                    .build();
            User savedUser = userRepository.save(user);
            String otp = otpService.generateOTP();


            Code code = Code.builder()
                    .oneTimePassword(otp)
                    .fromTime(LocalDateTime.now())
                    .expireTime(LocalDateTime.now().plusMinutes(2))
                    .status(OTPStatus.ALIVE)
                    .user(savedUser)
                    .build();

            codeRepository.save(code);
            emailService.sendEmail(user.getEmail(), "Your OTP Code", "Your OTP code is: " + otp);
            return ResponseEntity.ok(UserResponseDTO.builder()
                    .email(savedUser.getEmail())
                    .id(savedUser.getId())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the user.");
        }
    }

    @Override
    public ResponseEntity<?> getUserProfile(UUID userId) {
        UserProfileProjection userProfile = userRepository.getUserProfile(userId);
        System.out.println(userProfile);
        return ResponseEntity.ok(userProfile);
    }

    @Transactional
    public HttpEntity<?> checkOtp(String otpNumber, String email) {

        List<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.get(0);

        Optional<Code> codeOptional = codeRepository.findByUserId(user.getId());
     if (codeOptional.isPresent()) {

            Code code = codeOptional.get();
            String storedOtp = code.getOneTimePassword();

            if (code.getExpireTime().isBefore(LocalDateTime.now())) {
                codeRepository.deleteById(code.getId());
                return ResponseEntity.badRequest().body("OTP Code expired");
            }
            if (storedOtp == null) {
                return ResponseEntity.badRequest().body("No OTP found for this email");
            }
            if (!storedOtp.equals(otpNumber)) {
                return ResponseEntity.badRequest().body("Invalid OTP");
            }
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            codeRepository.deleteById(code.getId());
            return ResponseEntity.ok("OTP verified successfully");
        }
    else
    {
        return ResponseEntity.badRequest().body("No OTP found");
    }
}

    @Override
    @Transactional
    public ResponseEntity<?> edit(UUID userId, UserEditDTO userEditDto) throws IOException {


        if (!isValidPassword(userEditDto.getNewPassword())) {
            attachmentRepository.deleteById(userEditDto.getBannerAttachmentId());
            attachmentRepository.deleteById(userEditDto.getProfilePhotoAttachmentId());
            return ResponseEntity.badRequest().body("Invalid password");
        }
        Optional<User> byId = userRepository.findById(userId);
        System.out.println(userEditDto);
        if (byId.isPresent()){

            User byUsername = userRepository.findByUsername(userEditDto.getUsername());
            if (byUsername!=null){
                return ResponseEntity.badRequest().body("username invalid");
            }

            System.out.println("User is valid");
            User user = byId.get();
            if (passwordEncoder.matches(userEditDto.getOldPassword(), user.getPassword())){
                System.out.println("Password matched");
                user.setUsername(userEditDto.getUsername());
                user.setPassword(passwordEncoder.encode(userEditDto.getNewPassword()));
                user.setLastName(userEditDto.getLastName());
                user.setFirstName(userEditDto.getFirstName());
                if (userEditDto.getProfilePhotoAttachmentId()!=null){
                    if (user.getProfilePhoto()!=null) {
                        Attachment profilePhoto = user.getProfilePhoto();
                        attachmentRepository.deleteById(profilePhoto.getId());
                    }
                    UUID profilePhotoId = userEditDto.getProfilePhotoAttachmentId();
                    Attachment attachment = attachmentRepository.findById(profilePhotoId).get();
                    user.setProfilePhoto(attachment);
                }

                if (userEditDto.getBannerAttachmentId()!=null) {
                    bannerRepository.deleteByUserId(user.getId());
                    UUID bannerAttachmentId = userEditDto.getBannerAttachmentId();
                    Attachment attachment = attachmentRepository.findById(bannerAttachmentId).get();
                    Banner banner = Banner.builder()
                            .attachment(attachment)
                            .user(user)
                            .build();
                    bannerRepository.save(banner);
                }
                userRepository.save(user);
                System.out.println("OOOOOOOOOOOOOOO");
                return ResponseEntity.status(202).body("User successfully edited !!!");

            }
            else {
                return ResponseEntity.status(201).body("Password does not match");
            }
        }
        return ResponseEntity.status(201).body("User not found!!!");
    }

    @Override
    public ResponseEntity<?> getEditData(UUID userId) {

        try {
            List<UserEditProjection> userEditData = userRepository.getUserEditData(userId);
            return ResponseEntity.ok(userEditData);
        }
        catch (Exception e){
            return ResponseEntity.status(401).body("Not found user!!!");
        }
    }

    @Override
    public ResponseEntity<?> resend(ResendCodeDTO resendCodeDTO) {

        String otp = otpService.generateOTP();
        List<User> byEmail = userRepository.findByEmail(resendCodeDTO.getEmail());

        if (!byEmail.isEmpty()){
            User user = byEmail.get(0);
            Code code = Code.builder()
                    .status(OTPStatus.ALIVE)
                    .fromTime(LocalDateTime.now())
                    .expireTime(LocalDateTime.now().plusMinutes(2))
                    .user(user)
                    .oneTimePassword(otp)
                    .build();

            codeRepository.save(code);

            emailService.sendEmail(resendCodeDTO.getEmail(),"Your OTP Code","Your OTP code is "+otp);
            return ResponseEntity.ok("OTP code sent!");
        }
       else {
           return ResponseEntity.badRequest().body("Not found user with this email");
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> changePhoto(ChangePhotoDTO photoDTO) {

        Optional<User> userOptional = userRepository.findById(photoDTO.getUserId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(photoDTO.getAttachmentId());

            if (optionalAttachment.isPresent()){
                if (user.getProfilePhoto()!=null){
                    attachmentRepository.deleteById(user.getProfilePhoto().getId());
                }

                Attachment attachment = optionalAttachment.get();
                user.setProfilePhoto(attachment);
                userRepository.save(user);

                return ResponseEntity.ok("User photo set up!!!");
            }
            else {
                return ResponseEntity.badRequest().body("Photo not found!!!");
            }
        }
        else {
            return ResponseEntity.badRequest().body("User not found!!!");
        }

    }

    @Override
    public ResponseEntity<?> getId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byUsername = userRepository.findByUsername(username);
        if (byUsername==null){
            return ResponseEntity.badRequest().body("Not found user!!!");
        }
        return ResponseEntity.ok(byUsername.getId());
    }


    public User getMe(Principal principal){
        String username = principal.getName();
        return userRepository.findByUsername(username);
    }


     private boolean isValidPassword(String password) {
         String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n";
         Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

         if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }



}
