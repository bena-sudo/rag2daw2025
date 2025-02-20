-- Inserts para la tabla "documentos"
INSERT INTO documentos (id_doc_rag, id_usuario, nombre_fichero, comentario, base64_documento, extension_documento, content_type_documento, tipo_documento, estado_documento, fecha_creacion, fecha_revision)
VALUES 
(1, 101, 'documento1.pdf', 'Comentario sobre documento 1', 'dGVzdGRhdGE=', 'pdf', 'application/pdf', 'tipo1', 'PENDIENTE', '2025-02-01 10:00:00', NULL),
(2, 102, 'documento2.docx', 'Comentario sobre documento 2', 'dGVzdGRhdGEy=', 'docx', 'application/msword', 'tipo2', 'APROBADO', '2025-02-02 11:00:00', '2025-02-11 15:00:00'),
(3, 103, 'documento3.xlsx', 'Comentario sobre documento 3', 'dGVzdGRhdGEz=', 'xlsx', 'application/vnd.ms-excel', 'tipo1', 'DENEGADO', '2025-02-03 12:00:00', '2025-02-10 14:30:00'),
(4, 104, 'documento4.txt', 'Comentario sobre documento 4', 'dGVzdGRhdGE0=', 'txt', 'text/plain', 'tipo3', 'PENDIENTE', '2025-02-04 13:00:00', NULL),
(5, 105, 'documento5.jpg', 'Comentario sobre documento 5', 'dGVzdGRhdGE1=', 'jpg', 'image/jpeg', 'tipo4', 'PENDIENTE', '2025-02-05 14:00:00', NULL),
(6, 106, 'documento6.pdf', 'Comentario sobre documento 6', 'dGVzdGRhdGE2=', 'pdf', 'application/pdf', 'tipo5', 'APROBADO', '2025-02-06 15:00:00', '2025-02-09 10:00:00'),
(7, 107, 'documento7.png', 'Comentario sobre documento 7', 'dGVzdGRhdGE3=', 'png', 'image/png', 'tipo1', 'DENEGADO', '2025-02-07 16:00:00', '2025-02-08 16:00:00'),
(8, 108, 'documento8.txt', 'Comentario sobre documento 8', 'dGVzdGRhdGE4=', 'txt', 'text/plain', 'tipo2', 'PENDIENTE', '2025-02-08 17:00:00', NULL),
(9, 109, 'documento9.docx', 'Comentario sobre documento 9', 'dGVzdGRhdGE5=', 'docx', 'application/msword', 'tipo3', 'APROBADO', '2025-02-09 18:00:00', '2025-02-12 09:00:00'),
(10, 110, 'documento10.pdf', 'Comentario sobre documento 10', 'dGVzdGRhdGE=', 'pdf', 'application/pdf', 'tipo2', 'DENEGADO', '2025-02-10 19:00:00', '2025-02-07 12:00:00');


-- Inserts para la tabla "etiquetas"
INSERT INTO etiquetas (id, nombre)
VALUES 
(1, 'Importante'),
(2, 'Confidencial'),
(3, 'Cliente A'),
(4, 'Cliente B'),
(5, 'Revisión'),
(6, 'Interno'),
(7, 'Urgente'),
(8, 'Finalizado'),
(9, 'Pendiente'),
(10, 'Documentación');

-- Inserts para la tabla "documentos_etiquetas"
INSERT INTO documentos_etiquetas (id_documento, id_etiqueta)
VALUES 
(1, 1), (1, 5),
(2, 2), (2, 6),
(3, 3), (3, 7),
(4, 4), (4, 8),
(5, 1), (5, 9),
(6, 2), (6, 10),
(7, 3), (7, 4),
(8, 5), (8, 6),
(9, 7), (9, 8),
(10, 9), (10, 10);

-- Inserts para la tabla "documentos_chunks"
INSERT INTO documentos_chunks (id_documento, id_doc_rag, chunk_order, chunk_text, chunked_by, estado, fecha_creacion, fecha_modificacion)
VALUES 
(1, 1, 1, 'Texto del chunk 1 del documento 1', 101, 'PENDIENTE', '2025-02-01 10:00:00', NULL),
(1, 1, 2, 'Texto del chunk 2 del documento 1', 101, 'APROBADO', '2025-02-01 10:05:00', '2025-02-11 16:00:00'),
(2, 2, 1, 'Texto del chunk 1 del documento 2', 102, 'DENEGADO', '2025-02-02 11:10:00', '2025-02-10 15:00:00'),
(3, 3, 1, 'Texto del chunk 1 del documento 3', 103, 'PENDIENTE', '2025-02-03 12:15:00', NULL),
(4, 4, 1, 'Texto del chunk 1 del documento 4', 104, 'APROBADO', '2025-02-04 13:20:00', '2025-02-09 13:00:00'),
(5, 5, 1, 'Texto del chunk 1 del documento 5', 105, 'DENEGADO', '2025-02-05 14:25:00', '2025-02-08 11:00:00'),
(6, 6, 1, 'Texto del chunk 1 del documento 6', 106, 'MODIFICADO', '2025-02-06 15:30:00', '2025-02-07 17:00:00'),
(7, 7, 1, 'Texto del chunk 1 del documento 7', 107, 'PENDIENTE', '2025-02-07 16:35:00', NULL),
(8, 8, 1, 'Texto del chunk 1 del documento 8', 108, 'APROBADO', '2025-02-08 17:40:00', '2025-02-12 09:30:00'),
(9, 9, 1, 'Texto del chunk 1 del documento 9', 109, 'DENEGADO', '2025-02-09 18:45:00', '2025-02-06 10:00:00');


