window.onload = () => {
	const div = document.createElement('div');
	div.style.height = '100px';
	div.style.border = 'solid 1px #000';
	div.style.textAlign = 'center';
	div.style.lineHeight = '100px';
	div.textContent = 'THIS IF FROM THE JAVASCRIPT!!!';
	document.body.append(div);
}