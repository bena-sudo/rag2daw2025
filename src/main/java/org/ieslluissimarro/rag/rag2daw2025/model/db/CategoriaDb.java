// package org.ieslluissimarro.rag.rag2daw2025.model.db;

// import java.util.Set;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;



// @NoArgsConstructor
// @AllArgsConstructor
// @Data
// @Entity
// @Table(name = "categorias")
// public class CategoriaDb {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     @Column(nullable = false, unique = true, length = 100)
//     private String nombre;

//     @OneToMany(mappedBy = "categoria")
//     private Set<PermisoDb> permisos;
// }