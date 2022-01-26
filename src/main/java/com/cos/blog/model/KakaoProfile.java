package com.cos.blog.model;

import lombok.Data;

@Data //getter setter 생성
public class KakaoProfile {
	public Integer id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	@Data
	public class Properties {
		public String nickname;
		//public String profile_image; //유튜브엔 이 이미지에 대한 값이 있었는데 내 계정엔 이미지 없어서 없는듯
		//public String thumbnail_image; //유튜브엔 이 이미지에 대한 값이 있었는데 내 계정엔 이미지 없어서 없는듯
	}

	@Data
	public class KakaoAccount {
		public Boolean profile_nickname_needs_agreement;
		public Boolean profile_image_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

		@Data
		public class Profile {
			public String nickname;
			public String thumbnail_image_url;
			public String profile_image_url;
			public Boolean is_default_image;
		}
	}
}
