package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CONFIG_PAIS")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ConfigPais {
    @Id
    @Column(name = "ID_PAIS")
    private Long idPais;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_PAIS")
    private Pais pais;

    @Column(name = "USA_NIVEL_1")
    @Builder.Default
    private Boolean usaNivel1 = true;

    @Column(name = "USA_NIVEL_2")
    @Builder.Default
    private Boolean usaNivel2 = true;

    @Column(name = "USA_NIVEL_3")
    @Builder.Default
    private Boolean usaNivel3 = true;

    @Column(name = "LABEL_NIVEL_1", length = 50)
    @Builder.Default
    private String labelNivel1 = "Estado/Región";

    @Column(name = "LABEL_NIVEL_2", length = 50)
    @Builder.Default
    private String labelNivel2 = "Ciudad/Provincia";

    @Column(name = "LABEL_NIVEL_3", length = 50)
    @Builder.Default
    private String labelNivel3 = "Distrito/Localidad";

    @Column(name = "ZIP_REQUERIDO")
    @Builder.Default
    private Boolean zipRequerido = false;

    @Column(name = "FORMATO_TELEFONO", length = 50)
    private String formatoTelefono;
}
