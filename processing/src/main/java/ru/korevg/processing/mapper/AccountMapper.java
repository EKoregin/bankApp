package ru.korevg.processing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.korevg.processing.dto.NewAccountDTO;
import ru.korevg.processing.model.AccountEntity;

@Mapper
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    AccountEntity toEntity(NewAccountDTO newAccountDto);

    NewAccountDTO toDto(AccountEntity accountEntity);
}
