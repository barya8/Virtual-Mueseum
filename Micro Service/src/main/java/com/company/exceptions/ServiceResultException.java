package com.company.exceptions;

import com.company.model.ServiceResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResultException extends RuntimeException {
    private ServiceResult serviceResult;
}