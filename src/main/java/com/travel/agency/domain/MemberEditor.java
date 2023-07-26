package com.travel.agency.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberEditor {

    private final String email;
    private final String postcode;
    private final String address;
    private final String englishName;

    private MemberEditor(MemberEditorBuilder memberEditorBuilder) {
        this.email = memberEditorBuilder.getEmail();
        this.postcode = memberEditorBuilder.getPostcode();
        this.address = memberEditorBuilder.getAddress();
        this.englishName = memberEditorBuilder.getEnglishName();
    }

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MemberEditorBuilder {

        private String email;
        private String postcode;
        private String address;
        private String englishName;

        public MemberEditorBuilder email(String email) {
            if (email != null) {
                this.email = email;
            }
            return this;
        }

        public MemberEditorBuilder postcode(String postcode) {
            if (postcode != null) {
                this.postcode = postcode;
            }
            return this;
        }

        public MemberEditorBuilder address(String address) {
            if (address != null) {
                this.address = address;
            }
            return this;
        }

        public MemberEditorBuilder englishName(String englishName) {
            if (englishName != null) {
                this.englishName = englishName;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(this);
        }

    }

}
