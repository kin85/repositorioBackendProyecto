-- Eliminar tablas si existen
DROP TABLE IF EXISTS respuestas CASCADE;
DROP TABLE IF EXISTS cuestionarios CASCADE;
DROP TABLE IF EXISTS unidadades_competencia CASCADE;
DROP TABLE IF EXISTS unidades_competencia CASCADE;
DROP TABLE IF EXISTS modulos CASCADE;
DROP TABLE IF EXISTS sectores CASCADE;
DROP TABLE IF EXISTS documentos CASCADE;
DROP TABLE IF EXISTS estado_acreditacion CASCADE;
DROP TABLE IF EXISTS sesiones CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS tipo_pregunta CASCADE;
DROP TABLE IF EXISTS preguntas CASCADE;
DROP TABLE IF EXISTS usuario_modulos CASCADE;
DROP TABLE IF EXISTS usuario_unidadescompetencia CASCADE; 


-- Recrear las tablas


-- Tabla de sectores
CREATE TABLE sectores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);


-- Tabla de módulos
CREATE TABLE modulos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    sector_id INTEGER NOT NULL,
    nivel INTEGER CHECK (nivel BETWEEN 1 AND 3),  -- Nivel 1, 2 o 3
    FOREIGN KEY (sector_id) REFERENCES sectores(id) ON DELETE CASCADE
);


-- Tabla de unidades de competencia
CREATE TABLE unidades_competencia (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    modulo_id INTEGER NOT NULL,
    FOREIGN KEY (modulo_id) REFERENCES modulos(id) ON DELETE CASCADE
);


-- Tabla de cuestionarios
CREATE TABLE cuestionarios (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    unidad_competencia_id VARCHAR(10),
    FOREIGN KEY (unidad_competencia_id) REFERENCES unidades_competencia(id) ON DELETE SET NULL
);



-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  nickname VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  password_salt VARCHAR(255) NOT NULL,
  telefono VARCHAR(15),
  fecha_nacimiento DATE,
  estado VARCHAR(20) CHECK (estado IN ('activo', 'inactivo', 'pendiente', 'suspendido')) DEFAULT 'pendiente',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Tabla de sesiones
CREATE TABLE sesiones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE tipo_pregunta (
    id SERIAL PRIMARY KEY,            
    nombre VARCHAR(50) NOT NULL, 
    descripcion TEXT
);

-- Tabla de preguntas
CREATE TABLE preguntas (
    id SERIAL PRIMARY KEY,            
    texto TEXT NOT NULL,                  
    tipo_id INT NOT NULL,                          
    siguiente_si INT,                          
    siguiente_no INT,                             
    final_si BOOLEAN DEFAULT FALSE,               
    final_no BOOLEAN DEFAULT FALSE,               
    explicacion_si TEXT,                          
    explicacion_no TEXT,                          
    orden INT NOT NULL,                           
    cuestionario_id INT NOT NULL,  
    FOREIGN KEY (tipo_id) REFERENCES tipo_pregunta(id), 
    FOREIGN KEY (siguiente_si) REFERENCES preguntas(id), 
    FOREIGN KEY (siguiente_no) REFERENCES preguntas(id),
    FOREIGN KEY (cuestionario_id) REFERENCES cuestionarios(id)  
);


-- Tabla de respuestas
CREATE TABLE respuestas (
    id SERIAL PRIMARY KEY,
    pregunta_id INTEGER NOT NULL,
    usuario_id INTEGER,  
    respuesta TEXT NOT NULL,
    FOREIGN KEY (pregunta_id) REFERENCES preguntas(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);


-- Nueva tabla de documentos
CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    id_doc_rag INT,
    id_usuario BIGINT NOT NULL,
    nombre_fichero VARCHAR(255) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    tipo_documento VARCHAR(50),
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(100),
    estado_documento VARCHAR(20) DEFAULT 'pendiente', -- "pendiente", "aprobado", "denegado"
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_revision TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);


-- Tabla de estado de acreditación
CREATE TABLE estado_acreditacion (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL,
    modulo_id INTEGER NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('pendiente', 'aprobado', 'rechazado')) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (modulo_id) REFERENCES modulos(id) ON DELETE CASCADE
);

-- Tabla intermedia usuario_unidadescompetencia (se añadió estado)
CREATE TABLE usuario_unidadescompetencia (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL,
    unidad_competencia_id VARCHAR(10) NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('pendiente', 'aprobado', 'rechazado')) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (unidad_competencia_id) REFERENCES unidades_competencia(id) ON DELETE CASCADE
);

