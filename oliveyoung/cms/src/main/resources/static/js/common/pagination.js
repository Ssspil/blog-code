// 페이지 네이션 적용하기
function renderPagination(currentPage, pageSize, totalPages, totalElements, isLast, isFirst){
    const pagination = document.querySelector('.pagination');
    pagination.innerHTML = '';

    // 이전 버튼
    const prevDisabled = isFirst ? 'disabled' : '';
    pagination.innerHTML += `
        <li class="page-item ${prevDisabled}">
            <a class="page-link has-icon" href="javascript:void(0)" onclick="productList(${currentPage - 1})">
                <i class="material-icons">chevron_left</i>
            </a>
        </li>
    `;

    // 페이지 번호
    let startPage = Math.floor(currentPage / pageSize) * pageSize;
    let endPage = Math.min(startPage + 9, totalPages - 1);
    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            pagination.innerHTML += `
                <li class="page-item active">
                    <span class="page-link">${i + 1}</span>
                </li>
            `;
        } else {
            pagination.innerHTML += `
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0)" onclick="productList(${i})">${i + 1}</a>
                </li>
            `;
        }
    }

    // 다음 버튼
    const nextDisabled = isLast ? "disabled" : "";
    pagination.innerHTML += `
        <li class="page-item ${nextDisabled}">
            <a class="page-link has-icon" href="javascript:void(0)" onclick="productList(${currentPage + 1})">
                <i class="material-icons">chevron_right</i>
            </a>
        </li>
    `;
}