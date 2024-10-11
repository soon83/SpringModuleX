package com.soon83.member;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberResponseMapper {
	/**
	 * source 와 target 간 변수명이 다르다면, 아래처럼 맵핑 하십쇼
	 * @Mappings({
	 *     @Mapping(source = "loginId", target = "userLoginId"), // source 와 target 객체 간, 변수명이 다를 때 처리 방법
	 *     @Mapping(source = "product.itemCode", target = "itemCodeId", qualifiedByName = "mapObjectToLong"), // 객체가 null 일 수도 있을 때 처리 방법
	 * })
	 */
	MemberInfoResponse toMemberInfoResponse(MemberInfo memberInfo);

	MemberSearchCondition toMemberSearchCondition(MemberSearchRequest memberSearchRequest);

	/**
	 * Long 타입 처리 (예: customer.address.cityId 같은 경로에 대한 처리)
	 * 즉, map 을 여러번 해야할 경우는 직접 메서드를 만들어서 사용하자
	 */
    /*@Named("mapCustomerToCityId")
    public static Long mapCustomerToCityId(Customer customer) {
        return Optional.ofNullable(customer)
            .map(Customer::getAddress)
            .map(Address::getCityId)
            .orElse(null);
    }*/
}