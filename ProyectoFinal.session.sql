-- Eliminar tablas si existen
DROP TABLE IF EXISTS respuestas CASCADE;
DROP TABLE IF EXISTS cuestionarios_preguntas CASCADE;
DROP TABLE IF EXISTS cuestionarios CASCADE;
DROP TABLE IF EXISTS unidadades_competencia CASCADE;
DROP TABLE IF EXISTS unidades_competencia CASCADE;
DROP TABLE IF EXISTS modulos CASCADE;
DROP TABLE IF EXISTS sectores CASCADE;
DROP TABLE IF EXISTS documentos CASCADE;
DROP TABLE IF EXISTS estado_acreditacion CASCADE;
DROP TABLE IF EXISTS sesiones CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS preguntas CASCADE;
DROP TABLE IF EXISTS usuario_modulos CASCADE; 
DROP TABLE IF EXISTS tipo_pregunta;
DROP TABLE IF EXISTS usuario_unidadescompetencia CASCADE; 
DROP TABLE IF EXISTS usuarios_unidadescompetencia CASCADE;
DROP TABLE IF EXISTS mensajes CASCADE;

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


-- Tabla de preguntas
CREATE TABLE preguntas (
    id SERIAL PRIMARY KEY,
    texto VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    cuestionario_id INTEGER,
    FOREIGN KEY (cuestionario_id) REFERENCES cuestionarios(id) ON DELETE CASCADE
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

-- id, token UUID DEFAULT, metadata JSONB, fecha_creacion, fecha_ultima_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, usuario_id BIGINT REFERECNCES usuarios(id), fecha_usuario TIMESTAMP
-- Tabla de sesiones
CREATE TABLE sesiones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    usuario_id INTEGER,
    asesor_id INTEGER,
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

CREATE TABLE mensajes (
  id BIGSERIAL PRIMARY KEY,
  acreditacion_id BIGINT NOT NULL REFERENCES estado_acreditacion(id),
  usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
  contenido TEXT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos de prueba


-- Insertar sectores
INSERT INTO sectores (nombre) VALUES
('Tecnología'),
('Salud'),
('Arte');


-- Insertar módulos
INSERT INTO modulos (nombre, sector_id, nivel) VALUES
('Programación Básica', 1, 1),
('Desarrollo Web', 1, 2),
('Cuidado de la salud', 2, 1),
('Gestión de proyectos artísticos', 3, 3);


-- Insertar unidades de competencia
INSERT INTO unidades_competencia (id, nombre, modulo_id) VALUES
('UC001', 'Fundamentos de programación', 1),
('UC002', 'Programación orientada a objetos', 2),
('UC003', 'Primerosauxilios', 3);


-- Insertar usuarios
INSERT INTO usuarios (nombre, nickname, email, password, password_salt, telefono, fecha_nacimiento, estado) VALUES
('Juan Pérez', 'juanpe', 'juan@example.com', 'hashed_password_juan', 'salt_juan', '654321987', '1990-05-14', 'activo'),
('Ana López', 'anita', 'ana@example.com', 'hashed_password_ana', 'salt_ana', '612345678', '1988-09-22', 'pendiente'),
('Carlos Gómez', 'carlitos', 'carlos@example.com', 'hashed_password_carlos', 'salt_carlos', '678123456', '1995-03-10', 'suspendido');

-- Insertar relaciones entre usuarios y unidades de competencia
INSERT INTO usuario_unidadescompetencia (usuario_id, unidad_competencia_id, estado) VALUES
(1, 'UC001', 'pendiente'),
(1, 'UC002', 'aprobado'),
(2, 'UC003', 'pendiente');


-- Insertar cuestionarios
INSERT INTO cuestionarios (titulo, unidad_competencia_id) VALUES
('Cuestionario de Fundamentos de Programación', 'UC001'),
('Cuestionario de Programación Orientada a Objetos', 'UC002'),
('Cuestionario de Primeros Auxilios', 'UC003');

-- Insertar preguntas
INSERT INTO preguntas (texto, tipo, cuestionario_id) VALUES
('¿Qué es la programación?', 'texto', 1),
('¿Cuál es la diferencia entre un objeto y una clase?', 'texto', 1),
('¿Cómo se realiza la reanimación cardiopulmonar?', 'texto', 2),
('¿Qué es un proyecto artístico?', 'texto', 3);

-- Insertar respuestas
INSERT INTO respuestas (pregunta_id, usuario_id, respuesta) VALUES
(1, 1, 'Respuesta a la pregunta 1 por Juan Pérez'),
(2, 1, 'Respuesta a la pregunta 2 por Juan Pérez'),
(3, 2, 'Respuesta a la pregunta 3 por Ana López'),
(4, 3, 'Respuesta a la pregunta 4 por Carlos Gómez');

-- Insertar documentos
INSERT INTO documentos (id_doc_rag, id_usuario, nombre_fichero, comentario, base64_documento, tipo_documento, extension_documento, content_type_documento, estado_documento) VALUES
(1, 1, 'documento1.pdf', 'Comentario 1', 'base64string1', 'tipo1', '.pdf', 'application/pdf', 'pendiente'),
(2, 2, 'documento2.pdf', 'Comentario 2', 'base64string2', 'tipo2', '.pdf', 'application/pdf', 'aprobado'),
(3, 3, 'documento3.pdf', 'Comentario 3', 'base64string3', 'tipo3', '.pdf', 'application/pdf', 'denegado');

-- Insertar estado de acreditación
INSERT INTO estado_acreditacion (usuario_id, asesor_id, modulo_id, estado) VALUES
(1, 1, 1, 'pendiente'),
(2, 2, 2, 'aprobado'),
(3, 3, 3, 'rechazado');

-- Insertar conversaciones
INSERT INTO mensajes (acreditacion_id, usuario_id, contenido) VALUES
(1, 1, 'Mensaje de prueba 1'),
(2, 2, 'Mensaje de prueba 2'),
(3, 3, 'Mensaje de prueba 3');