document.addEventListener('DOMContentLoaded', function () {
    const statusForm = document.getElementById('statusForm');

    statusForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const orderID = document.getElementById('orderID').value;
        const status = document.getElementById('status').value;

        // 여기에서 서버로 상태 업데이트 요청을 보내는 코드를 추가
        // 실제로는 백엔드 서버와 통신하여 데이터를 업데이트
        console.log(`주문 ID: ${orderID}, 상태: ${status}`);
    });
});
