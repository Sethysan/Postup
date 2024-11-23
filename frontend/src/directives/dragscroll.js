export default {
    mounted(el) {
      let isDragging = false;
      let startY;
      let scrollTop;
  
      el.addEventListener('mousedown', (e) => {
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
        const y = e.pageY - el.offsetTop;
        const walk = (y - startY) * 2;
        el.scrollTop = scrollTop - walk;
      });
    }
  };