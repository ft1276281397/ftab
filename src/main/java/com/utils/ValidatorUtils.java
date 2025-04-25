package com.utils;

// 导入Set接口，用于存储集合
import java.util.Set;

// 导入ConstraintViolation类，用于表示校验约束违规信息
import javax.validation.ConstraintViolation;
// 导入Validation类，用于构建验证器工厂
import javax.validation.Validation;
// 导入Validator接口，用于执行验证
import javax.validation.Validator;

// 导入EIException类，用于自定义异常
import com.entity.EIException;

/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
    // 静态变量，用于存储Validator实例
    private static Validator validator;

    // 静态代码块，初始化Validator实例
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws EIException  校验不通过，则抛出EIException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws EIException {
        // 执行校验并获取约束违规信息集合
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        // 如果存在约束违规信息
        if (!constraintViolations.isEmpty()) {
            // 获取第一个约束违规信息
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            // 抛出自定义异常，包含违规信息的消息
            throw new EIException(constraint.getMessage());
        }
    }
}
