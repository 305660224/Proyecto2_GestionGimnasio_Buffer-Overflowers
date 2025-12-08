/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.PagoDTO;
import Modelo.Pago;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PagoMapper {
    
    /**
     * De pago "modelo" a pagoDto
     * @param pago
     * @return 
     */
    public static PagoDTO toDTO(Pago pago) {
        if (pago == null) return null;
        
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setCedulaCliente(pago.getCedulaCliente());
        dto.setFechaPago(pago.getFechaPago());
        dto.setSubtotal(pago.getSubtotal());
        dto.setImpuesto(pago.getImpuesto());
        dto.setTotal(pago.getTotal());
        return dto;
    }
    
    /**
     * De pagoDTO a pago "modelo"
     * @param dto
     * @return 
     */
    public static Pago toEntity(PagoDTO dto) {
        if (dto == null) return null;
        
        return new Pago(
            dto.getIdPago(),
            dto.getCedulaCliente(),
            dto.getFechaPago(),
            dto.getSubtotal(),
            dto.getImpuesto(),
            dto.getTotal()
        );
    }
    
    /**
     * De lista de Pago a lista de PagoDTO
     * @param pagos
     * @return 
     */
    public static List<PagoDTO> toDTOList(List<Pago> pagos) {
        if (pagos == null) return null;
        
        List<PagoDTO> dtos = new ArrayList<>();
        for (Pago pago : pagos) {
            dtos.add(toDTO(pago));
        }
        return dtos;
    }
    
    /**
     * De lista de pagoDTO a lista de pago
     * @param dtos
     * @return 
     */
    public static List<Pago> toEntityList(List<PagoDTO> dtos) {
        if (dtos == null) return null;
        
        List<Pago> pagos = new ArrayList<>();
        for (PagoDTO dto : dtos) {
            pagos.add(toEntity(dto));
        }
        return pagos;
    }
}
