package com.example.microservicio_monopatin.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoPorKilometroDto {
    private Long idMonopatin;
    private Long kmRecorridos;
}