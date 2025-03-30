# **신사 숙녀 쇼핑몰**
### 📖 프로젝트 개요 (Overview / Description)

- **20, 30대를 위한 깔끔하고 트랜디**한 UI/UX를 제공하는 **의류 쇼핑몰**
- 벤치마킹 쇼핑몰: **무신사 스토어** (https://www.musinsa.com/main/musinsa/recommend)
- 무신사처럼 **온라인 패션 스토어**이며, **자체 상표 브랜드**
- **모던/베이식/캐주얼**한 의류 제품을 공장에서부터 **입고, 진열, 관리, 판매, 배송, 고객 관리** 등을 한 번에 관리할 수 있는 **자체 쇼핑몰**
- **다양한 구매 방식**을 제공해 **사용자 만족도**를 높임

### 🛠️ 기술 스택 (Tech Stack)

- Java 21
- Spring Boot 3.2.0
- MySQL 8.0.41
- MyBatis

### ✅ 진행 상황 (progress)

1. **프로젝트 구현 및 실행 조건** - 2025.03.10 ~ 2025.03.16 (완)
    - ERD툴을 이용한 논리 모델링 작성
    - 물리 모델링 & 테이블 생성 및 데이터 입력 스크립트 작성
    - MySQL8.x 데이터 베이스에 물리 모델 구축
2. **서비스 구현** - 2025.03.16 ~ 2025.03.19 (완)
    - 기술 버전 결정
    - 각자 화면 정의서, 요구사항 정의서, 기술 정의서를 바탕으로 각 파트 구현
3. **테스트** - 2025.03.20 ~ 2025.03.28 (완)
    - 각자 개발한 파트 유닛 테스트 진행
    - 원활한 사용자 UX를 위해 예외 처리
    - 전체적인 흐름에서 부터 세부적인 흐름으로 들어가면서 정의서와 맞게 구현되어 있는지 체크
4. **발표 자료 정리 및 준비** - 2025.03.28 ~ 2025.03.30 
    - 각자 개발한 서비스 정리
    - 흐름도 정리
    - README.md 업데이트
5. **발표** - 2025.03.31
    - 본인 파트 발표 및 Q&A
6. **리팩토링 및 Spring/JPA 변환** - 2025.04.01 ~ 2025.04.30
    - Spring boot, MyBatis 를 Spring 으로 변환해보기 (선택)


### 📂 폴더 구조 (Optional but helpful)
- **src/main**
  - **/java/org/example/shoppingmall** : 구현한 JAVA 파일들이 저장되는 구조
    - /config : 설정 관련 클래스
    - /controller : 클라이언트 요청을 처리하는 컨트롤러
    - /dto : 요청 및 응답을 위한 DTO
    - /repository : DB 접근을 담당하는 인터페이스
    - /service : 비즈니스 로직을 처리하는 서비스 클래스
  - **/resources** :
    - /mapper : MyBatis SQL 매핑 파일
    - /mybatis : MyBatis 설정 파일
    - /static : 정적 리소스 (CSS, JS, 이미지)
    - /templates : Thymeleaf 템플릿 (HTML 파일)
- 각 폴더마다 자신이 맡은 파트(complaint, user, order, payment, product, shipping)로 나뉨

### 🚀 시작 방법 (Getting Started)
1. Clone the repository
2. git clone https://github.com/kimyelin0506/KDT_DBE3_Toy_Project1.git
3. Setup DB and environment
4. Run the application

### 👤 팀원 소개 (Optional)

#### *ERD 설계 담당 파트를 기준으로 개발 또한 관련 파트로 나눔*

- 김우주: **[주문]** 관련 파트
- 김예린: **[상품]** 관련 파트
- 김예지: **[배송]** 관련 파트
- 문지환: **[결제]** 관련 파트
- 이병우: **[취소/교환/환불]** 관련 파트
- 이민형: **[고객], [장바구니]** 관련 파트

### 📌 주요 기능 (Features)

1. **고객**
    - **고객**
        - 로그인
        - 회원가입 기능
        - 마이페이지 기능
        - 장바구니
2. **상품**
    - **고객**
        - 상품 정렬(내림차순/별점/후기개수/좋아요 수/누적판매량)
        - 상품 검색
        - 상품 카테고리
        - 상품의 상세 정보 제공
        - 상품의 수량, 등록된 옵션을 선택
    - **관리자**
        - 상품의 진열 상태 수정(진열/판매 중지/품절)
        - 새로운 상품 등록
3. **주문**
    - **고객**
        - 상품 주문 기능
        - 상품주문 목록 확인
        - 주문 내역 삭제
        - 상세 주문 확인
    - **관리자**
        - 전체 주문 이력 확인
4. **결제**
    - **고객**
        - 결제수단 및 현금영수증 정보 입력 후 결제 시 주문/결제/배송 및 이력 저장
        - 거래명세서 출력 기능
    - **관리자**
        - 가상계좌 결제 조회/수정 기능
5. **배송**
   - **구매자**
      - 배송 현황 조회
   - **판매자**
      - 배송 건에 대한  발송 관리
      - 발송한 상품에 대한 상태 관리
6. **취소/환불/교환**
   - **구매자**
      - 취소,환불,교환 각각의 유형에 맞는 민원 신청 기능
      - 신청한 민원 수정 및 정보 확인 기능
      - 신청한 민원의 처리 상태 확인 기능
   - **관리자**
      - 고객이 신청한 민원 답변 기능
      - 고객이 신청한 민원 정보 확인 기능

### 📌 ERD 설계 (ERD design)
![prj02_erd.png](project_info/prj02_erd.png)
- 검은 색: 고객/장바구니
- 노란 색: 상품
- 빨간 색: 주문
- 파란 색: 결제
- 하늘 색: 배송
- 초록 색: 취소/환불/교환
- 보라 색: 직원/관리자

### 📌 API 명세서 (API Specification)

(1) 고객

| 역할   | 설명            |method|URL|
|------|---------------|-------|---|
| [고객] | 로그인 페이지 불러오기  |	GET|	/user/login|
| [고객] | 로그인 페이지 불러오기	 |GET|	/user/login|
| [고객] | 유저 로그인        |	POST| /user/login|
| [고객] | 유저 로그아웃	      | POST          |  /user/logout |
| [고객] | 마이페이지 페이지	    | GET	          | /user/mypage  |
|[고객] |마이페이지 데이터|	GET|	/user/mypage/data|
|[고객] |회원가입 페이지|	GET|	/user/register|
|[고객] |아이디 중복체크|	POST| /user/registerCheck|
|[고객] |닉네임 중복 확인|	POST| /user/checkNickname|
|[고객] |회원가입 버튼 api|	POST| /user/register|
|[고객] |회원 정보 수정 api|	POST| /user/modify|
|[고객] |이미지 업로드 api|	POST| /user/uploadProfileImage|
|[고객] |배송지 관리 페이지 api|	GET|	/user/addressmanage|
|[고객] |modal 띄울때 페이지 데이터 api|	GET|	/user/addressmanage/{customerId}|
|[고객] |modal 주소 정보 등록 완료 눌릴때 api|	POST| /user/addressmanage|
|[고객] |modal 주소 정보 수정 완료 눌릴때 api|	POST| /user/addressmanage/update|
|[고객]| 주소 정보 삭제 할때 api|	POST| /user/addressmanage/delete|

(2) 상품

| 역할     | 설명                                         | METHOD | URL                                              |
|----------|----------------------------------------------|--------|--------------------------------------------------|
| [고객]   | 메인 페이지                                | GET    | /                                                |
| [고객]   | 상품 상세 페이지                          | GET    | /productDetail/{prdId}                          |
| [고객]   | 카테고리 페이지 (대분류)                  | GET    | /category/{majorCID}                            |
| [고객]   | 카테고리 페이지 (중분류)                  | GET    | /category/{majorCID}/{midCID}                   |
| [고객]   | 카테고리 페이지 (소분류)                  | GET    | /category/{majorCID}/{midCID}/{subCID}          |
| [고객]   | 메인/카테고리 페이지 상품 정렬             | GET    | /**/{orderOption}                               |
| [고객]   | 메인/카테고리 페이지 상품 검색             | GET    | /**/{searchProduct}                             |
| [고객]   | 메인/카테고리 페이지 상품 정렬 + 검색      | GET    | /**/{searchProduct}&{orderOption}              |
| [관리자] | 상품 관리 페이지                          | GET    | /admin/product                                  |
| [관리자] | 상품 진열 상태 변경                        | POST   | /admin/product                                  |
| [관리자] | 상품 정보 수정 페이지                     | GET    | /admin/product/updatePorductDetail             |
| [관리자] | 상품 정보 수정                            | POST   | /admin/product/updatePorductDetail/{prdId}     |
| [관리자] | 새로운 상품 등록 페이지                   | GET    | /admin/product/addNewProduct                   |
| [관리자] | 새로운 상품 등록                          | POST   | /admin/product/addNewProduct                   |


(3) 주문

| 역할   | 설명                         | METHOD | URL |
|--------|------------------------------|--------|----------------------------------|
| [고객] | 주문서 (상품 페이지)         | GET    | /order/order                   |
| [고객] | 주문 (장바구니)               | POST   | /order/order                   |
| [고객] | 주문 목록                     | GET    | /order/list                    |
| [고객] | 주문 상세                     | GET    | /order/detail/{orderId}        |
| [고객] | 주문 내역 삭제                | GET    | /order/delete/{orderId}        |
| [관리자] | 전체 주문 조회               | GET    | /admin/order                   |

(4) 결제

| 역할     | 설명                    | METHOD | URL                          |
|----------|-------------------------|--------|------------------------------|
| [고객]   | 결제하기                | POST   | /payment                     |
| [고객]   | 거래명세서 조회          | GET    | /payment/receipt/{orderId}   |
| [관리자] | 가상계좌 결제 조회       | GET    | /payment/admin               |
| [관리자] | 가상계좌 결제 상태 변경  | PUT    | /payment/admin               |

(5) 배송

| 역할     | 설명                      | METHOD | URL                      |
|----------|---------------------------|--------|--------------------------|
| [관리자] | 배송 리스트 전체 조회      | GET    | /admin/shipping/list     |
| [관리자] | 배송 조회 상세 페이지      | GET    | /detail/{id}             |
| [관리자] | 배송 상세 수정 페이지      | GET    | /update/{id}             |
| [관리자] | 배송 상세 수정 후 저장     | POST   | /update/{id}             |
| [고객]   | 배송 조회 페이지          | GET    | /shipping/track          |

(6) 취소/환불/교환

| 역할     | 설명                | METHOD | URL                                  |
|----------|---------------------|--------|--------------------------------------|
| [고객]   | 민원 신청 페이지    | GET    | /complaint/{orderId}                |
| [고객]   | 민원 신청           | POST   | /complaint/request                  |
| [고객]   | 민원 리스트 페이지  | GET    | /complaint/list                     |
| [고객]   | 민원 상세 페이지    | GET    | /complaint/detail/{complaintId}     |
| [고객]   | 민원 수정 페이지    | GET    | /complaint/edit                     |
| [고객]   | 민원 수정           | POST   | /complaint/update                   |
| [고객]   | 민원 삭제           | POST   | /complaint/delete/{complaintId}     |
| [관리자] | 민원 리스트 페이지  | GET    | /admin/complaint/list               |
| [관리자] | 민원 상세 페이지    | GET    | /admin/complaint/detail/{complaintId} |
| [관리자] | 민원 접수           | POST   | /admin/complaint/receive/{complaintId} |
| [관리자] | 민원 답변 페이지    | GET    | /admin/complaint/{complaintId}      |
| [관리자] | 민원 답변           | POST   | /admin/complaint/response/{complaintId} |

### 📌 흐름도 (Flowchart)

### 📌 화면 정의서 (Screen Definition)





