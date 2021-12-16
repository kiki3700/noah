
# 마네키 네코 프로젝트란?

 마네키 네코 프로젝트는 계량적 방법론과 투자자동화를 통해 과학적 투자를 위한 웹어플리케이션입니다. 원하는 정보를 편리하게 시각화하고, 이를 통해 투자의사결정을 내릴 수 있고, 다양한 전략을 비교해 볼수 있도록 할 수 있는 금융 샌드박스를 만드는 것이 목적입니다.
 
 # Ark
 해당 레포지토리는 외부 api를 이용해 데이터를 취득하고 매매를 처리하는 Ark 서버 레포지토리입니다.

# 인프라 스트럭쳐와 도메인

![infraDomain](https://user-images.githubusercontent.com/74307591/144598474-f2f060fe-d123-4d06-97d7-1cc022b055ef.png)



 마네키네코 프로젝트는 두개의 서버와 클라우드 데이터 베이스로 이뤄져있습니다. 금융 정보를 시각화하고 투자의사결정을 내릴 수 있는 FE를 담당하는 Ubuntu 서버(Noah)와 외부 api를 이용하여 데이터를 취득하고 대신증권의 api인 Cybos를 이용하여 트래이딩을 담당하는 윈도우 서버(Ark)로 이뤄져 있습니다.  두 서버는 직접적으로 통신을 하지 않고 클라우드 db통해 간접적으로 통신을 하게 됩니다. 

# ERD

![erd](https://user-images.githubusercontent.com/74307591/144598497-f93ed01e-7245-45e5-9b05-23842a38f67b.png)


# 포트폴리오 구축 프로세스

![포트폴리오등록프로세스](https://user-images.githubusercontent.com/74307591/144598516-1e27b728-7473-4c79-8eef-40a111ccbc45.png)

# 트레이딩 프로세스

![트레이딩프로세스](https://user-images.githubusercontent.com/74307591/144598541-98edf932-3236-4363-8a4e-5c909dbf6f7a.png)


# 기여자

[이성현](https://github.com/kiki3700/)

[김현진](https://github.com/KimHyeonJins)

# Ark 개발환경

__OS :__ window10

__language :__ java 8 (32bit)

__library ;__ com4j

__framework :__ spring boot

__database :__ oracle, mybatis

__etc :__ git, CybosPlus

# Noah 개발환경

__OS :__ window10

__language :__ java 8 (32bit), java script

__library :__ OjAlgo, google chart

__framewok :__ spring boot

__database :__ oracle, mybatis

__etc :__ git,  figma
