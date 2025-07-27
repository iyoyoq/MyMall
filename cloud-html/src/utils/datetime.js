export function getCountdown(endDateString) {
  const endDate = new Date(endDateString);
  const now = new Date();
  const diff = endDate - now;

  if (diff <= 0) return "已截止";

  const totalSeconds = Math.floor(diff / 1000);
  const days = Math.floor(totalSeconds / 86400);
  const hours = Math.floor((totalSeconds % 86400) / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;

  let result = '';
  if (days > 0) result += `${days}天`;
  if (days > 0 || hours > 0) result += `${hours}时`;
  if ((days > 0 || hours > 0) || minutes > 0) result += `${minutes}分`;
  result += `${seconds}秒`;

  return result;
}
