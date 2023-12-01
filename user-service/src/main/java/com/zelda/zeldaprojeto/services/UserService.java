package com.zelda.zeldaprojeto.services;

        import com.zelda.zeldaprojeto.models.UserModel;
        import com.zelda.zeldaprojeto.repositories.UserRepository;
        import lombok.RequiredArgsConstructor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Service;


        import java.util.List;
        @Service
        @RequiredArgsConstructor
public class UserService {

            private final UserRepository userRepository;

            public ResponseEntity<Page<UserModel>> acharTodosUsuarios(Pageable pageable) {
                return ResponseEntity.ok(userRepository.findAll(pageable));
            }

            public ResponseEntity<UserModel> adicionarUsuario(UserModel user) {
                return ResponseEntity.ok(userRepository.save(user));
            }


            public ResponseEntity<List<UserModel>> buscarUsuario(Long id) {
                if (userRepository.findById(id).isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(userRepository.findByid(id));
            }

            public ResponseEntity<UserModel> editarUsuario(Long id_user, UserModel user) {
                if (userRepository.existsById(id_user)) {
                    user.setId(id_user);
                    return ResponseEntity.ok(userRepository.save(user));
                }
                return ResponseEntity.notFound().build();
            }
        }

