package com.dev.accounts.service.impl;

import com.dev.accounts.dto.AccountsDto;
import com.dev.accounts.dto.CardsDto;
import com.dev.accounts.dto.CustomerDetailsDto;
import com.dev.accounts.dto.LoansDto;
import com.dev.accounts.entity.Accounts;
import com.dev.accounts.entity.Customer;
import com.dev.accounts.exception.ResourceNotFoundException;
import com.dev.accounts.mapper.AccountsMapper;
import com.dev.accounts.mapper.CustomerMapper;
import com.dev.accounts.repository.AccountsRepository;
import com.dev.accounts.repository.CustomerRepository;
import com.dev.accounts.service.ICustomerService;
import com.dev.accounts.service.client.CardsFeignClient;
import com.dev.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loansDtoResponseEntity!=null){
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(cardsDtoResponseEntity != null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
