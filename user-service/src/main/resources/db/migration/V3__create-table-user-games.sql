CREATE TABLE user_games (
  user_id INTEGER REFERENCES users(id),
  game_id INTEGER REFERENCES games(id_banco),
  PRIMARY KEY (user_id, game_id)
);



INSERT INTO user_games(user_id, game_id) VALUES
  (1, '2'),
  (1, '5'),
  (2, '8'),
  (3, '3'),
  (4, '10'),
  (5, '19'),
  (6, '15'),
  (7, '4'),
  (8, '3'),
  (9, '1'),
  (9, '7'),
  (10, '13'),
  (11, '2');
