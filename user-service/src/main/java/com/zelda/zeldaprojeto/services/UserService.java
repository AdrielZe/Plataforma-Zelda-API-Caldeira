package com.zelda.zeldaprojeto.services;

        import com.zelda.zeldaprojeto.models.UserModel;
        import com.zelda.zeldaprojeto.repositories.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Service;


        import java.util.List;
        @Service
public class UserService {

            @Autowired
            private UserRepository userRepository;

            public ResponseEntity<List<UserModel>> acharTodosUsuarios() {
                return ResponseEntity.ok(userRepository.findAll());
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

            public ResponseEntity<String> deletarUsuario(Long id) {
                if (userRepository.existsById(id)) {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok("Usuário deletado com sucesso");
                }
                return ResponseEntity.notFound().build();
            }
        }

