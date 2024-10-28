package com.soon83.interfaces.member;

import com.soon83.dtos.member.*;
import com.soon83.utils.AssertUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	/**
	 * source 와 target 간 변수명이 다르다면, 아래처럼 맵핑 하십쇼
	 * @Mappings({
	 *     @Mapping(source = "loginId", target = "userLoginId"), // source 와 target 객체 간, 변수명이 다를 때 처리 방법
	 *     @Mapping(source = "product.itemCode", target = "itemCodeId", qualifiedByName = "mapObjectToLong"), // 객체가 null 일 수도 있을 때 처리 방법
	 * })
	 */
	MemberSearchCondition toMemberSearchCondition(MemberSearchRequest request);

	MemberInfoResponse toMemberInfoResponse(MemberInfo info);

	MemberCreateCommand toMemberCreateCommand(MemberRegisterRequest request);

	MemberUpdateCommand toMemberUpdateCommand(MemberEditRequest request);

	MemberDeleteCommand toMemberDeleteCommand(MemberRemoveRequest request);

	@Mapping(source = "requestList", target = "commandList")
	MemberBulkCreateCommand toMemberBulkCreateCommand(MemberBulkRegisterRequest request);

	@Mapping(source = "requestList", target = "commandList")
	MemberBulkUpdateCommand toMemberBulkUpdateCommand(MemberBulkEditRequest request);

	default MemberBulkDeleteCommand toMemberBulkDeleteCommand(List<Long> memberIdList) {
		AssertUtil.isTrue(!memberIdList.isEmpty(), "회원 아이디 목록은 최소 1개 이상 입력해 주세요.");
		List<MemberDeleteCommand> commandList = memberIdList.stream()
				.map(MemberDeleteCommand::new)
				.toList();
		return new MemberBulkDeleteCommand(commandList);
	}

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