document.addEventListener('DOMContentLoaded', function () {
    const statusForm = document.getElementById('statusForm');

    statusForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const orderID = document.getElementById('orderID').value;
        const status = document.getElementById('status').value;

        // Fetch API를 사용하여 서버로 상태 업데이트 요청 보내기
        fetch(`/api/updateOrderStatus/${orderID}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ status: status }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // 서버 응답을 기반으로 추가적인 작업 수행
                console.log(data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});
