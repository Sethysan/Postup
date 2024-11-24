export default {
  mounted(el) {
    let isDragging = false;
    let startY;
    let scrollTop;

    el.addEventListener('mousedown', (e) => {
      e.preventDefault();
      e.stopPropagation();
      isDragging = true;
      startY = e.pageY - el.offsetTop;
      scrollTop = el.scrollTop;
      el.style.cursor = 'grabbing';
    });

    el.addEventListener('mouseleave', () => {
      isDragging = false;
      el.style.cursor = 'grab';
    });

    el.addEventListener('mouseup', () => {
      isDragging = false;
      el.style.cursor = 'grab';
    });

    el.addEventListener('mousemove', (e) => {
      if (!isDragging) return;
      e.preventDefault();
      e.stopPropagation();
      const y = e.pageY - el.offsetTop;
      const walk = (y - startY) * 1;
      el.scrollTop = scrollTop - walk;
    });

    el.addEventListener('touchstart', (e) => {
      isDragging = true;
      startY = e.touches[0].pageY - el.offsetTop;
      scrollTop = el.scrollTop;
    });

    el.addEventListener('touchend', () => {
      isDragging = false;
    });

    el.addEventListener('touchmove', (e) => {
      if (!isDragging) return;
      e.preventDefault();
      const y = e.touches[0].pageY - el.offsetTop;
      const walk = (y - startY) * 2; // Adjust sensitivity
      el.scrollTop = scrollTop - walk;
    });
  }
};