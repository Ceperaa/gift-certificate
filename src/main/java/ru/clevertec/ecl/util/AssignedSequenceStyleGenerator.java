package ru.clevertec.ecl.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AssignedSequenceStyleGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if (obj instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) obj;
            Serializable id = identifiable.getId();
            if (id != null) {
                return id;
            }
        }
        return super.generate(session, obj);
    }
}
