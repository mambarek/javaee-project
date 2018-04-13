package com.it2go.employee.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it2go.framework.entities.IAbstractEntity;
import com.it2go.framework.util.json.JsonStdDateSerializer;
import lombok.Getter;
import lombok.Setter;
import org.reflections.ReflectionUtils;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DomainEntity implements IAbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    //@JsonSerialize(using = JsonStdDateSerializer.class)
    private Date createdAt;

    //@Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    private Person createdBy;

    @OneToOne
    private Person updatedBy;

    @Override
    public boolean equals(Object o) {
        // the same object
        if (this == o) {
            return true;
        }
        // check if same class
        if (this.getClass() != o.getClass()) {
            return false;
        }

        DomainEntity other = (DomainEntity) o;

        //check lstupdate the DB entity should not be changed
        if (!Objects.equals(this.updatedAt, other.updatedAt)) {
            return false;
        }

        // check if same id
        if (this.getId() != null && this.getId().equals(other.getId())) {
            return true;
        }

        // same class no id so return super.equals
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int hashCode = 11; // willkürlicher Initialwert
        int multi = 29; // nicht zu große, zufällig gewählte Primzahl als Multiplikator
        hashCode += (this.getId() == null) ? 0 : this.getId().hashCode();

        if (this.updatedAt != null)
            hashCode = hashCode * multi + this.getUpdatedAt().hashCode();

        return hashCode;
    }

    public boolean isValid(){
        Set<Field> allFields = ReflectionUtils.getAllFields(this.getClass());
        for (Field field: allFields) {
            field.setAccessible(true);
            final NotNull annotation = field.getAnnotation(NotNull.class);
            try {
                Object value = field.get(this);
                if(annotation != null &&  value == null)
                    return false;
                // recursion of children
                if(value != null && DomainEntity.class.isAssignableFrom(field.getType())){
                    DomainEntity entity = (DomainEntity) field.get(this);
                    if(!entity.isValid()) return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
