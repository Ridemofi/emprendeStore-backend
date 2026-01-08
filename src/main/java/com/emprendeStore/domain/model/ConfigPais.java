package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "config_pais")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ConfigPais {
    @Id
    @Column(name = "id_pais")
    private Long idPais;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pais")
    private Pais pais;

    @Column(name = "usa_nivel_1")
    @Builder.Default
    private Boolean usaNivel1 = true;

    @Column(name = "usa_nivel_2")
    @Builder.Default
    private Boolean usaNivel2 = true;

    @Column(name = "usa_nivel_3")
    @Builder.Default
    private Boolean usaNivel3 = true;

    @Column(name = "label_nivel_1", length = 50)
    @Builder.Default
    private String labelNivel1 = "Estado/Región";

    @Column(name = "label_nivel_2", length = 50)
    @Builder.Default
    private String labelNivel2 = "Ciudad/Provincia";

    @Column(name = "label_nivel_3", length = 50)
    @Builder.Default
    private String labelNivel3 = "Distrito/Localidad";

    @Column(name = "zip_requerido")
    @Builder.Default
    private Boolean zipRequerido = false;

    @Column(name = "formato_telefono", length = 50)
    private String formatoTelefono;
}
