package com.pedro.budget.dto;

import java.util.UUID;

import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.dto.validation.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequest(
        @NotNull(message = "Description is required", groups = {
                OnCreate.class }) @NotBlank(message = "Description cannot be blank", groups = {
                        OnCreate.class, OnUpdate.class }) String description,
        @NotNull(message = "Card number is required", groups = {
                OnCreate.class }) @NotBlank(message = "Card number cannot be blank", groups = {
                        OnCreate.class, OnUpdate.class }) String cardNumber,
        @NotNull(message = "Due day is required", groups = {
                OnCreate.class }) Integer dueDay,
        @NotNull(message = "Closing day is required", groups = {
                OnCreate.class }) Integer closingDay,
        @NotNull(message = "Bank account ID is required", groups = {
                OnCreate.class }) @NotBlank(message = "Bank account ID cannot be blank", groups = {
                        OnCreate.class, OnUpdate.class }) UUID bankAccountId) {

}
