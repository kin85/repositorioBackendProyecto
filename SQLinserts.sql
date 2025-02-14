-- Insertar sectores
INSERT INTO sectores (nombre) VALUES
('Informática');


-- Insertar módulos
INSERT INTO modulos (nombre, sector_id, nivel) VALUES
('Operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos ', 1, 1),
('Operaciones auxiliares con tecnologías digitales', 1, 1),
('Operación de sistemas microinformáticos ', 1, 2),
('Creación y publicación de páginas web ', 1, 2),
('Montaje y reparación de sistemas microinformáticos', 1, 2),
('Operación de redes locales', 1, 2),
('Operaciones de seguridad en sistemas informáticos', 1, 2),
('Operación en sistemas de comunicaciones de voz y datos ', 1, 2),
('Operaciones de mantenimiento de sistemas de radiocomunicaciones ', 1, 2),
('Digitalización aplicada al entorno profesional', 1, 2),
('Operaciones con tecnologías habilitadoras digitales', 1, 2),
('Operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos', 1, 3),
('Montaje y mantenimiento de sistemas microinformáticos', 1, 3),
('Sistemas microinformáticos', 1, 3),
('Operación de redes departamentales', 1, 3),
('Administración de servicios de internet', 1, 3),
('Administración de bases de datos', 1, 3),
('Programación en lenguajes estructurados de aplicaciones de gestión', 1, 3),
('Programación con lenguajes orientados a objetos y bases de datos relacionales', 1, 3),
('Desarrollo de aplicaciones con tecnologías web', 1, 3),
('Administración de sistemas operativos', 1, 3),
('Administración de redes y seguridad', 1, 3),
('Desarrollo de aplicaciones para la gestión empresarial y de negocio', 1, 3);

-- Insertar unidades de competencia
INSERT INTO unidades_competencia (id, nombre, modulo_id) VALUES
('UC1208_1', 'Realizar operaciones auxiliares de mantenimiento de sistemas microinformáticos', 1),
('UC1209_1', 'Realizar operaciones auxiliares con tecnologías de la información y la comunicación', 1),
('UC2741_1', 'Operaciones auxiliares de manejo de información, creación de contenidos y comunicación usando medios digitales', 2),
('UC2742_1', 'Operaciones auxiliares de seguridad y de resolución de problemas hardware', 2),
('UC0219_2', 'Gestionar el software de base en sistemas microinformáticos', 3),
('UC0221_2', 'Gestionar aplicaciones en sistemas microinformáticos', 3),
('UC0222_2', 'Dar soporte al usuario de equipos microinformáticos', 3),
('UC0233_2', 'Manejar aplicaciones ofimáticas en la gestión de la información y la documentación', 3),
('UC0950_2', 'Crear páginas web', 4),
('UC0951_2', 'Integrar componentes software en páginas web', 4),
('UC0952_2', 'Publicar páginas web', 4),
('UC2817_2', 'Aplicar estilos gráficos a páginas web', 4),
('UC0953_2', 'Montar equipos microinformáticos', 5),
('UC0954_2', 'Mantener equipamiento microinformático', 5),
('UC0220_2', 'Instalar dispositivos de comunicaciones en redes locales', 6),
('UC0955_2', 'Mantener los procesos de comunicaciones en redes locales', 6),
('UC2688_2', 'Interconectar redes privadas y públicas', 6),
('UC0957_2', 'Mantener el subsistema físico en sistemas informáticos', 7),
('UC0958_2', 'Mantener el software de base y las aplicaciones en sistemas informáticos', 7),
('UC0959_2', 'Configurar la ciberseguridad en equipos finales', 7),
('UC2797_2', 'Configurar la seguridad en redes de comunicaciones y sistemas de correo electrónico', 7),
('UC2798_2', 'Responder ante eventos de ciberseguridad', 7);
-- Insertar cuestionarios
INSERT INTO cuestionarios (titulo, unidad_competencia_id) VALUES
('Autoevaluacion', NULL);

INSERT INTO cuestionarios (titulo, unidad_competencia_id) VALUES
('Autoevaluacion2', null);

-- Insertar tipos de pregunta
INSERT INTO tipo_pregunta (nombre, descripcion) 
VALUES 
('SI_NO', 'Preguntas que tienen una respuesta de sí o no.'),
('RANGO_1A4', 'Preguntas donde la respuesta es un valor numérico entre 1 y 4.');


-- Insertar preguntas
-- Insertar preguntas con la clave ajena al tipo y otros campos completos
INSERT INTO preguntas (texto, tipo_id, siguiente_si, siguiente_no, final_si, final_no, explicacion_si, explicacion_no, orden, cuestionario_id) VALUES
(E'Pregunta 1: ¿Tienes la nacionalidad en regla? ¿Posees alguno de los siguientes documentos?:\n 
-Nacionalidad Española\n
-Certificado de Registro de ciudadanía comunitaria o tarjeta de familiar de ciudadano o ciudadana de la Unión\n
-Autorización de residencia o, de residencia y trabajo en España en vigor, conforme a los términos establecidos en la normativa de estrangería e inmigración\n
', 
1, 
2, 3, FALSE, TRUE, 
'Eres elegible para continuar el proceso si tienes los documentos mencionados.',
'Sin nacionalidad, no hay acreditación. PUERTA. Y cierra al salir.',
1, 1),

('Pregunta 2: Al realizar la inscripción, ¿tienes 20 años cumplidos? ', 
1, 
4, 5, FALSE, FALSE, 
'Puedes continuar con la inscripción si tienes 20 años o más.',
'No puedes continuar con la inscripción si no tienes 20 años.',
2, 1),

('Pregunta 3: Al realizar la inscripción, ¿tienes 18 años cumplidos?', 
1, 
6, 7, FALSE, FALSE, 
'Puedes continuar con la inscripción si tienes 18 años o más.',
'No puedes continuar con la inscripción si no tienes 18 años.',
3, 1),

('Pregunta 4: ¿En los últimos 15 años, tienes experiencia laboral relacionada con el sector profesional en el cual quieres que se te reconozca la competencia profesional de al menos 3 años y 200 horas?', 
1, 
8, NULL, FALSE, FALSE, 
'Si tienes la experiencia solicitada, puedes proceder con el reconocimiento.',
'Si no tienes la experiencia solicitada, no puedes proceder con el reconocimiento.',
4, 1),

('Pregunta 5: ¿En los últimos 15 años tienes experiencia laboral relacionada con el sector profesional en el cual quieres que se te reconozca la competencia profesional de al menos 2 años y 1200 horas?', 
1, 
NULL, NULL, FALSE, FALSE, 
'Puedes continuar con el proceso si cumples con los requisitos de experiencia.',
'No puedes continuar sin cumplir con los requisitos de experiencia.',
5, 1),

('Pregunta 6: ¿En los últimos 10 años ¿tienes formación no formal relacionada con el sector profesional en el cual quieres que se te reconozcla la competencia profesional de al menos 300 horas?', 
1, 
NULL, NULL, FALSE, FALSE, 
'La formación de 300 horas es suficiente para continuar con el proceso.',
'La falta de formación de 300 horas te impide continuar.',
6, 1),

('Pregunta 7: ¿En los últimos 10 años ¿tienes formación no formal relacionada con el sector profesional en el cual quieres que se te reconozcla la competencia profesional de al menos 300 horas?', 
1, 
NULL, NULL, FALSE, FALSE, 
'Si tienes la formación suficiente, puedes seguir adelante.',
'Si no tienes la formación suficiente, no puedes continuar.',
7, 1),

('Pregunta 8: ¿En los últimos 10 años ¿tienes formación no formal relacionada con el sector profesional en el cual quieres que se te reconozcla la competencia profesional de al menos 200 horas?', 
1, 
NULL, NULL, FALSE, FALSE, 
'Puedes avanzar si cumples con la formación mínima de 200 horas.',
'No puedes avanzar sin la formación mínima de 200 horas.',
8, 1);

UPDATE preguntas
SET explicacion_no = 'Sin nacionalidad, no hay acreditación. PUERTA. Y cierra al salir.'
WHERE id = 1;

DELETE FROM preguntas 
WHERE id IN (
    SELECT id FROM preguntas 
    ORDER BY id 
    LIMIT 8
);


-- Insertar usuarios
INSERT INTO usuarios (nombre, nickname, email, password, password_salt, telefono, fecha_nacimiento, estado) VALUES
('Juan Pérez', 'juanp', 'juan@dominio.com', 'hashedpassword1', 'salt1', '1234567890', '1990-05-15', 'activo'),
('Ana Gómez', 'anag', 'ana@dominio.com', 'hashedpassword2', 'salt2', '0987654321', '1985-07-20', 'pendiente');


