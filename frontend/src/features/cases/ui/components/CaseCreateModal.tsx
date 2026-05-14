import type { FormEvent } from 'react'

import type { CaseDraft } from '../../domain/caseDraft'
import { caseStatusLabels, caseStatusOptions } from '../../domain/casePresentation'
import { inputClasses, primaryButtonClasses, secondaryButtonClasses } from '../caseTheme'

type Props = {
  draft: CaseDraft
  onDraftChange: (draft: CaseDraft) => void
  onSubmit: () => Promise<boolean>
  error: string
  onClose: () => void
}

export function CaseCreateModal({ draft, onDraftChange, onSubmit, error, onClose }: Props) {
  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    const success = await onSubmit()
    if (success) onClose()
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4" onClick={onClose}>
      <div className="absolute inset-0 bg-slate-950/60 backdrop-blur-sm" />

      <div
        className="relative z-10 w-full max-w-lg overflow-hidden rounded-3xl bg-white shadow-[0_40px_120px_rgba(15,23,42,0.3)]"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="p-6 sm:p-8">
          <div className="flex items-start justify-between gap-4">
            <div>
              <p className="text-sm font-semibold uppercase tracking-[0.3em] text-orange-700">Kreiranje</p>
              <h2 className="mt-2 font-display text-3xl text-slate-950">Novi predmet</h2>
            </div>
            <button
              type="button"
              className="mt-1 rounded-full p-2 text-slate-400 transition-colors hover:bg-stone-100 hover:text-slate-700"
              onClick={onClose}
              aria-label="Zatvori"
            >
              <svg width="18" height="18" viewBox="0 0 18 18" fill="none">
                <path d="M3 3L15 15M15 3L3 15" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
              </svg>
            </button>
          </div>

          {error ? (
            <p className="mt-4 rounded-xl border border-rose-200 bg-rose-50 px-3 py-2 text-sm font-semibold text-rose-700" role="alert">
              {error}
            </p>
          ) : null}

          <form className="mt-6 space-y-4" onSubmit={(e) => void handleSubmit(e)}>
            <div className="space-y-1.5">
              <label className="text-sm font-semibold text-slate-700" htmlFor="create-case-title">
                Naziv
              </label>
              <input
                className={inputClasses}
                id="create-case-title"
                type="text"
                placeholder="npr. Pregled ugovora za Acme d.o.o."
                value={draft.title}
                onChange={(e) => onDraftChange({ ...draft, title: e.target.value })}
                autoFocus
              />
            </div>
            <div className="space-y-1.5">
              <label className="text-sm font-semibold text-slate-700" htmlFor="create-case-status">
                Status
              </label>
              <select
                className={inputClasses}
                id="create-case-status"
                value={draft.status}
                onChange={(e) => onDraftChange({ ...draft, status: e.target.value as CaseDraft['status'] })}
              >
                <option value="" disabled>
                  Odaberite status
                </option>
                {caseStatusOptions.map((s) => (
                  <option key={s} value={s}>
                    {caseStatusLabels[s]}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex gap-3 pt-2">
              <button type="submit" className={primaryButtonClasses}>
                Kreiraj predmet
              </button>
              <button type="button" className={secondaryButtonClasses} onClick={onClose}>
                Otkaži
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}
