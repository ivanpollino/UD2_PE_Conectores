-- Tabla para la configuración del jugador
CREATE TABLE IF NOT EXISTS PlayerConfig (
    id INTEGER PRIMARY KEY AUTOINCREMENT,  
    control_settings TEXT,                 
    sound_enabled BOOLEAN,                 
    resolution TEXT,                       
    language TEXT                          
);

-- Tabla para el estado del jugador
CREATE TABLE IF NOT EXISTS PlayerState (
    player_id INTEGER PRIMARY KEY,         
    nick_name TEXT,                        
    experience INTEGER,                    
    life_level INTEGER,                    
    coins INTEGER,                         
    session_count INTEGER,
	 last_login DATE                  
);

DROP TABLE PlayerState;