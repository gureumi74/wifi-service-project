// 사용자의 현재 위치 가져오기
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else {
        console.error("Geolocation 은 이 브라우저에서 지원되지 않습니다.");
    }
}

// 위치 정보 가져오기 성공 시 실행
function showPosition(position) {
    var lat = position.coords.latitude; // 위도
    var lnt = position.coords.longitude; // 경도

    // 위치 정보 input 태그에 채우기
    document.getElementById("lat").value = lat;
    document.getElementById("lnt").value = lnt;

    console.log("위치 정보 로드 성공");
}

// 에러 처리
function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            console.error("사용자가 위치 정보를 허용하지 않았습니다.");
            break;
        case error.POSITION_UNAVAILABLE:
            console.error("위치 정보를 사용할 수 없습니다.");
            break;
        case error.TIMEOUT:
            console.error("요청 시간이 초과되었습니다.");
            break;
        case error.UNKNOWN_ERROR:
            console.error("알 수 없는 오류가 발생했습니다.");
            break;
    }
}
