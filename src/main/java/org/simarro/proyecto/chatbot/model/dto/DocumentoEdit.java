package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentoEdit {
    private Long id;
    private int idDocRag;
    private long usuario_id;
    private String nombreFichero;
    private String comentario;
    private String base64Documento;
    private String extensionDocumento;
    private String contentTypeDocumento;
    private String estadoDocumento;
    private java.sql.Timestamp fechaCreacion;
    private java.sql.Timestamp fechaRevision;
}
