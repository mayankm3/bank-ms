package com.dev.loans.service.impl;

import com.dev.loans.dto.LoansDto;
import com.dev.loans.entity.Loans;
import lombok.AllArgsConstructor;
import com.dev.loans.exception.LoanAlreadyExistsException;
import com.dev.loans.exception.ResourceNotFoundException;
import com.dev.loans.mapper.LoansMapper;
import com.dev.loans.repository.LoansRepository;
import com.dev.loans.service.ILoansService;
import org.springframework.stereotype.Service;
import com.dev.loans.constants.LoansConstants;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber){
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);

        return Loans.builder()
                .loanNumber(Long.toString(randomLoanNumber))
                .mobileNumber(mobileNumber)
                .loanType(LoansConstants.HOME_LOAN)
                .totalLoan(LoansConstants.NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(LoansConstants.NEW_LOAN_LIMIT)
                .build();
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber())
        );

        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
