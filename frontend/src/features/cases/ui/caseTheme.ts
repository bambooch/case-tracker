export const statusBadgeClasses: Record<string, string> = {
  OPEN: 'bg-emerald-100 text-emerald-900 ring-1 ring-inset ring-emerald-200',
  IN_REVIEW: 'bg-amber-100 text-amber-900 ring-1 ring-inset ring-amber-200',
  CLOSED: 'bg-slate-200 text-slate-700 ring-1 ring-inset ring-slate-300',
}

export const attentionBadgeClasses: Record<string, string> = {
  IMMEDIATE: 'bg-rose-100 text-rose-800 ring-1 ring-inset ring-rose-200',
  FOLLOW_UP: 'bg-sky-100 text-sky-800 ring-1 ring-inset ring-sky-200',
  ARCHIVE: 'bg-stone-200 text-stone-700 ring-1 ring-inset ring-stone-300',
}

export const attentionLabels: Record<string, string> = {
  IMMEDIATE: 'Hitno',
  FOLLOW_UP: 'Kontrola',
  ARCHIVE: 'Arhiva',
}

export const inputClasses =
  'w-full rounded-2xl border border-stone-300 bg-white/90 px-4 py-3 text-slate-900 shadow-sm outline-none transition focus:border-orange-400 focus:ring-4 focus:ring-orange-100'

export const primaryButtonClasses =
  'inline-flex items-center justify-center rounded-full bg-slate-950 px-5 py-3 text-sm font-semibold text-stone-50 transition hover:bg-slate-800 focus:outline-none focus:ring-4 focus:ring-slate-300 disabled:cursor-not-allowed disabled:opacity-60'

export const secondaryButtonClasses =
  'inline-flex items-center justify-center rounded-full border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 transition hover:border-orange-300 hover:text-slate-950 focus:outline-none focus:ring-4 focus:ring-orange-100'

export const dangerButtonClasses =
  'inline-flex items-center justify-center rounded-full border border-rose-200 bg-rose-50 px-4 py-2 text-sm font-semibold text-rose-700 transition hover:bg-rose-100 focus:outline-none focus:ring-4 focus:ring-rose-100'
