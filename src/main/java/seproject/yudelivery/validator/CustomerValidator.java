package seproject.yudelivery.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seproject.yudelivery.dto.UpdateCustomerForm;

@Component
@Slf4j
public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateCustomerForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateCustomerForm form = (UpdateCustomerForm)target;
        log.info("valid target = {}",form);

        if(!StringUtils.hasText(form.getNickName())){
            errors.rejectValue("nickName","required");
        }
        if(form.getPassword() == null){
            errors.rejectValue("password","required");
        }
        if(!StringUtils.hasText(form.getAddress())){
            errors.rejectValue("address","required");
        }
        if(form.getPhone() == null){
            errors.rejectValue("phone","required");
        }
    }
}
