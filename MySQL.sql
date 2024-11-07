
CREATE TABLE IF NOT EXISTS PlayerState (
    player_id INT PRIMARY KEY,             
    experience INT,                        
    life_level INT,                        
    coins INT,                             
    session_count INT,                     
    last_login DATETIME                    
);

-- Tabla para los datos del videojuego
CREATE TABLE IF NOT EXISTS GameData (
    game_id INT PRIMARY KEY,               
    isbn VARCHAR(20) UNIQUE,               
    title VARCHAR(255),                    
    player_count INT,                      
    total_sessions INT,                    
    last_session DATETIME                  
);

-- Tabla para los datos de las partidas
CREATE TABLE IF NOT EXISTS GameSessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,  
    game_id INT,                                
    player_id INT,                              
    experience INT,                             
    life_level INT,                             
    coins INT,                                  
    session_date DATETIME,                      
    FOREIGN KEY (game_id) REFERENCES GameData(game_id),
    FOREIGN KEY (player_id) REFERENCES PlayerState(player_id)
);
