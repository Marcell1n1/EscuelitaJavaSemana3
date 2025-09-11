package model;

@Entity @Table(name="detalles_pedido")
@Getter @Setter
public class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="pedido_id")
    private Pedido pedido;

    @Column(name="producto_id", nullable=false)
    private Long productoId;

    @Column(name="producto_nombre", nullable=false)
    private String productoNombre;

    @Column(name="precio_unitario", nullable=false, precision=19, scale=2)
    private BigDecimal precioUnitario;

    @Column(nullable=false)
    private Integer cantidad;

    @Column(nullable=false, precision=19, scale=2)
    private BigDecimal subtotal;
}