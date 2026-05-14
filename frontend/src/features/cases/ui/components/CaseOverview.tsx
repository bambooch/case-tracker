type CaseOverviewProps = {
  total: number
  open: number
  inReview: number
}

export function CaseOverview({ total, open, inReview }: CaseOverviewProps) {
  return (
    <div className="rounded-[2rem] border border-white/70 bg-white/75 p-6 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur sm:p-8">
      <p className="mb-3 text-sm font-semibold uppercase tracking-[0.3em] text-orange-700">Case tracker workspace</p>
      <h1 className="font-display text-4xl leading-tight sm:text-5xl">Cases</h1>
      <p className="mt-4 max-w-2xl text-lg text-slate-600">
        Manage intake, triage, and follow-up from one board. Open a case to view notes, participants, and edit details.
      </p>
      <div className="mt-8 grid gap-4 sm:grid-cols-3">
        <article className="rounded-3xl bg-slate-950 px-5 py-4 text-left text-stone-50 shadow-lg">
          <p className="text-sm uppercase tracking-[0.24em] text-orange-300">Total</p>
          <p className="mt-3 text-3xl font-semibold">{total}</p>
        </article>
        <article className="rounded-3xl border border-amber-200 bg-amber-50 px-5 py-4 text-left shadow-sm">
          <p className="text-sm uppercase tracking-[0.24em] text-amber-700">In review</p>
          <p className="mt-3 text-3xl font-semibold text-slate-900">{inReview}</p>
        </article>
        <article className="rounded-3xl border border-emerald-200 bg-emerald-50 px-5 py-4 text-left shadow-sm">
          <p className="text-sm uppercase tracking-[0.24em] text-emerald-700">Open</p>
          <p className="mt-3 text-3xl font-semibold text-slate-900">{open}</p>
        </article>
      </div>
    </div>
  )
}
