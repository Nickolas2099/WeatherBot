CREATE TABLE log_message_table (
                                   id SERIAL PRIMARY KEY,
                                   telegram_user_id BIGINT NOT NULL,
                                   user_message TEXT NOT NULL,
                                   message_time TIMESTAMP NOT NULL,
                                   bot_answer TEXT NOT NULL
);

CREATE INDEX idx_telegram_user_id ON log_message_table(telegram_user_id);

INSERT INTO log_message_table (telegram_user_id, user_message, message_time, bot_answer)
VALUES ('163145953', '/start', '2024-10-10 00:00:00', 'Привет, Дмитрий! Для того чтобы узнать погоду напиши /weather <город>'),
       ('972842842', '/start', '2024-10-10 00:00:00', 'Привет, Алексей! Для того чтобы узнать погоду напиши /weather <город>'),
       ('294697578', '/weather', '2024-10-10 00:00:00', 'Необходимо указать город на латинице после команды /weather');